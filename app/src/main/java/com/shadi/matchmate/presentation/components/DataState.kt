package com.shadi.matchmate.presentation.components

 data class DataState<T> (
   val isLoading :Boolean=false,
   val error:Throwable?=null,
   val data:T?=null
)