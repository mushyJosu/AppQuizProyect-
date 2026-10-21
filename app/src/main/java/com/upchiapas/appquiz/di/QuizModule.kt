package com.upchiapas.appquiz.di

import android.content.Context
import com.upchiapas.appquiz.data.datasource.SeedDataSource
import com.upchiapas.appquiz.data.datasource.UserQuestionsDataSource
import com.upchiapas.appquiz.data.repository.PreguntaRepository
import com.upchiapas.appquiz.data.repository.PreguntaRepositoryImpl
import com.upchiapas.appquiz.presentation.viewmodels.QuizViewModelFactory

class QuizModule(private val context: Context) {
    private val seedDataSource: SeedDataSource by lazy { SeedDataSource() }

    private val userDataSource: UserQuestionsDataSource by lazy { UserQuestionsDataSource(context) }

    private val repository: PreguntaRepository by lazy {
        PreguntaRepositoryImpl(seedDataSource, userDataSource)
    }

    val factory: QuizViewModelFactory by lazy { QuizViewModelFactory(repository) }
}