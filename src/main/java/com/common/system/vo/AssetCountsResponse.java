package com.common.system.vo;

import java.util.Map;

public class AssetCountsResponse {
    private Map<Integer, Integer> assetCounts;

    public AssetCountsResponse() {
    }

    public AssetCountsResponse(Map<Integer, Integer> assetCounts) {
        this.assetCounts = assetCounts;
    }

    public Map<Integer, Integer> getAssetCounts() {
        return assetCounts;
    }

    public void setAssetCounts(Map<Integer, Integer> assetCounts) {
        this.assetCounts = assetCounts;
    }
}
