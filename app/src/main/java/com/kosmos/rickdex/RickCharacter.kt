package com.kosmos.rickdex

import com.google.gson.annotations.SerializedName

data class RickCharacter (@SerializedName("id") var id:Int,
                      @SerializedName("name") var name:String,
                      @SerializedName("status") var status:String,
                      @SerializedName("species") var species:String,
                      @SerializedName("type") var type:String,
                      @SerializedName("gender") var gender:String,
                      @SerializedName("origin") var origin:Origin,
                      @SerializedName("location") var location:Location,
                      @SerializedName("image") var image:String)  //var images: List<String>)

