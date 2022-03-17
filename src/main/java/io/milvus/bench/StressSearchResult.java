package io.milvus.bench;

import io.milvus.response.SearchResultsWrapper;

import java.util.List;

public class StressSearchResult {
    private List<Integer> ids;
    private SearchResultsWrapper searchResultsWrapper;
    private long latencyMs;
    private boolean dummy; // mark the end of searching process

    public void setDummy(boolean dummy) {
        this.dummy = dummy;
    }

    public boolean getDummy() {
        return this.dummy;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return this.ids;
    }

    public void setSearchResultsWrapper(SearchResultsWrapper searchResultsWrapper) {
        this.searchResultsWrapper = searchResultsWrapper;
    }

    public SearchResultsWrapper getSearchResultsWrapper() {
        return searchResultsWrapper;
    }

    public void setLatencyMs(long latencyMs) {
        this.latencyMs = latencyMs;
    }

    public long getLatencyMs() {
        return this.latencyMs;
    }
}
