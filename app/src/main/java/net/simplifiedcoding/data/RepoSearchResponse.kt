package net.simplifiedcoding.data

import com.example.android.codelabs.paging.model.Repo
import com.google.gson.annotations.SerializedName

class RepoSearchResponse {
    @SerializedName("total_count") val total: Int = 0
    @SerializedName("items") val items: List<Repo> = emptyList()
    val nextPage: Int? = null
}
