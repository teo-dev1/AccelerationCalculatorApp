package com.example.punchspeedcalculator.utils

interface RemoteServiceProvider<T> {
    fun provideDatabaseSevice():T

}
interface RemoteLoginServiceProvider<T>{
    fun provideLoginService():T
}