package io.milvus.bench;

import io.milvus.param.MetricType;

import java.util.List;

public class StressSearchParameters {
    private String collection;
    private int topK;
    private List<Integer> ids;
    private List<String> partitions;
    private List<List<Float>> embeddings;
    private MetricType metricType;

    public void setMetricType(MetricType metricType) {
        this.metricType = metricType;
    }

    public MetricType getMetricType() {
        return this.metricType;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getCollection() {
        return collection;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public int getTopK() {
        return topK;
    }

    public List<String> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<String> partitions) {
        this.partitions = partitions;
    }

    public List<List<Float>> getEmbeddings() {
        return embeddings;
    }

    public void setEmbeddings(List<List<Float>> embeddings) {
        this.embeddings = embeddings;
    }
}
