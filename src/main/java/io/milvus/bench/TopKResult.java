package io.milvus.bench;

import java.util.List;

public class TopKResult {
    private int id;
    private List<Long> topKResults;
    private int recallCount;
    private int topK;

    public void setRecallCount(int recallCount) {
        this.recallCount = recallCount;
    }

    public int getRecallCount() {
        return recallCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTopKResults(List<Long> topKResults) {
        this.topKResults = topKResults;
    }

    public List<Long> getTopKResults() {
        return topKResults;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public int getTopK() {
        return topK;
    }
}
