package com.udacity.politcalpreparedness.network

import okhttp3.OkHttpClient

class CivicsHttpClient : OkHttpClient() {

    companion object {

        private const val API_KEY = "AIzaSyCoU1BpTFt3V8VG7aFEC_HcUR-vG1qSv64"

        fun getClient(): OkHttpClient {
            return Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", API_KEY)
                        .build()
                    val request = original
                        .newBuilder()
                        .url(url)
                        .build()
                    chain.proceed(request)
                }
                .build()
        }

    }

}