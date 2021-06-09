package net.simplifiedcoding.ui

import androidx.paging.PagingSource
import com.example.android.codelabs.paging.model.Repo
import net.simplifiedcoding.data.MyApi
import net.simplifiedcoding.repository.GithubRepository.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException
private const val GITHUB_STARTING_PAGE_INDEX = 1
class PassengersDataSource(
    private val api: MyApi, private  val query:String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX

        return try {
            val response = api.searchRepos(query, position, params.loadSize)
            val repos = response.items
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}