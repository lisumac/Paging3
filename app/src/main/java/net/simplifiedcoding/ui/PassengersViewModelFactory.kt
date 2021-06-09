package net.simplifiedcoding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.simplifiedcoding.data.MyApi
import net.simplifiedcoding.repository.GithubRepository

@Suppress("UNCHECKED_CAST")
class PassengersViewModelFactory(
    private val api: GithubRepository
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PassengersViewModel(api) as T
    }
}