package io.milvus.bench;

import io.milvus.bench.utils.Loader;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DescribeIndexResponse;
import io.milvus.grpc.SearchResults;
import io.milvus.grpc.ShowCollectionsResponse;
import io.milvus.param.ConnectParam;
import io.milvus.param.R;
import io.milvus.param.collection.HasCollectionParam;
import io.milvus.param.collection.ShowCollectionsParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.DescribeIndexParam;
import io.milvus.response.DescIndexResponseWrapper;
import io.milvus.response.SearchResultsWrapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Milvus {
    public static final Properties PROPERTIES = new Properties();
    public static final MilvusServiceClient milvusClient;
    public static final List<List<Float>> QUERY_EMBEDDINGS;

    static {
        // load the properties files
        InputStream propIn = Main.class.getClassLoader().getResourceAsStream("prop.properties");
        try {
            PROPERTIES.load(propIn);
            System.out.println("PROPERTIES loaded: " + PROPERTIES.size());
            propIn.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // build milvus client
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost(PROPERTIES.getProperty("MILVUS_HOST"))
                .withPort(Integer.valueOf(PROPERTIES.getProperty("MILVUS_PORT")))
                .build();
        milvusClient = new MilvusServiceClient(connectParam);
        // load queries
        InputStream queryIn = Main.class.getClassLoader().getResourceAsStream(PROPERTIES.getProperty("QUERY_FILE"));
        QUERY_EMBEDDINGS = Loader.loadFloatArrayFromCsv(queryIn);

    }

    private static void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException(r.getMessage());
        }
    }

    private static R<Boolean> hasCollection(String collectionName) {
        System.out.println("========== hasCollection() ==========");
        R<Boolean> response = milvusClient
                .hasCollection(HasCollectionParam.newBuilder()
                        .withCollectionName(collectionName)
                        .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private static R<DescribeIndexResponse> describeIndex(String collectionName) {
        System.out.println("========== describeIndex() ==========");
        R<DescribeIndexResponse> response = milvusClient.describeIndex(DescribeIndexParam.newBuilder()
                .withCollectionName(collectionName)
                .withFieldName(PROPERTIES.getProperty("VECTOR_FIELD"))
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    public static R<ShowCollectionsResponse> showCollections() {
        System.out.println("========== showCollections() ==========");
        R<ShowCollectionsResponse> response = milvusClient.showCollections(ShowCollectionsParam.newBuilder()
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private static DescIndexResponseWrapper describeIndexInfo(String collectionName) {
        System.out.println("========== describeIndex() ==========");
        R<DescribeIndexResponse> response = milvusClient.describeIndex(DescribeIndexParam.newBuilder()
                .withCollectionName(collectionName)
                .withFieldName(PROPERTIES.getProperty("VECTOR_FIELD"))
                .build());
        handleResponseStatus(response);

        DescIndexResponseWrapper wrapper = new DescIndexResponseWrapper(response.getData());
        DescIndexResponseWrapper.IndexDesc indexDesc =
                wrapper.getIndexDescByFieldName(PROPERTIES.getProperty("VECTOR_FIELD"));
        System.out.println(indexDesc.getFieldName());
        System.out.println(indexDesc.getIndexType());
        System.out.println(indexDesc.getMetricType());

        System.out.println(response);
        return wrapper;
    }

    public static void runQuickSearch(String collectionName) {
        DescIndexResponseWrapper.IndexDesc indexDesc =
                describeIndexInfo(collectionName)
                        .getIndexDescByFieldName(PROPERTIES.getProperty("VECTOR_FIELD"));
        int topK = 20;
        int nq = 5;
        final List<List<Float>> vectors = new ArrayList();
        List<Long> ids = new ArrayList();
        for (int i = 0; i < nq; i++) {
            vectors.add(QUERY_EMBEDDINGS.get(i));
            ids.add((long)i);
        }

        final String SEARCH_PARAM = "{\"nprobe\":64}";
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(collectionName)
                .withTopK(topK)
                .withMetricType(indexDesc.getMetricType())
                .withVectors(vectors)
                .withVectorFieldName(PROPERTIES.getProperty("VECTOR_FIELD"))
                .withParams(SEARCH_PARAM)
                .build();
        long begin = System.currentTimeMillis();
        R<SearchResults> response = milvusClient.search(searchParam);
        long end = System.currentTimeMillis();
        long cost = end - begin;
        System.out.println("Search time cost: " + cost + "ms");
        handleResponseStatus(response);
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
        List<List<Long>> results = new ArrayList();
        for (int i = 0; i < vectors.size(); ++i) {
            System.out.println("Search result of No." + i);
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            System.out.println(scores);
            List<Long> result = new ArrayList();
            for (SearchResultsWrapper.IDScore score : scores) {
                result.add(score.getLongID());
            }
            if (!result.isEmpty()) {
                results.add(result);
            }
        }
    }
}
