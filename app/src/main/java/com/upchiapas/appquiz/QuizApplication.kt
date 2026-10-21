package com.upchiapas.appquiz

import android.app.Application
import com.upchiapas.appquiz.di.QuizModule

class QuizApplication : Application() {
    val quizModule: QuizModule by lazy { QuizModule(this) }
}