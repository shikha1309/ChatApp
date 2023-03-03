package com.shikha.chatapp.ModelClasses
// all the var name should be same as we created for reference database
class Users  {
    private var uid: String =""
    private var username: String =""
    private var profile: String =""
    private var cover: String =""
    private var status: String =""
    private var search: String =""
    private var facebook: String =""
    private var instagram: String =""
    private var portfolio: String =""


    // this is the default constructor with no parameter
    constructor()

      // This is the constructor  with No parameter
     constructor(
        uid: String,
        username: String,
        profile: String,
        cover: String,
        status: String,
        search: String,
        facebook: String,
        instagram: String,
        portfolio: String
    ) {
        this.uid = uid
        this.username = username
        this.profile = profile
        this.cover = cover
        this.status = status
        this.search = search
        this.facebook = facebook
        this.instagram = instagram
        this.portfolio = portfolio
    }

    // for Accessing the data we have to call default getter and setter methods for each variable
    fun getUID():String ?{
        return uid
    }
    fun setUID(uid :String ) {
        this.uid =uid

    }


    fun getUserName():String ?{
        return username
    }
    fun setUserName(username :String ) {
        this.username =username

    }

    fun getProfile():String ?{
        return profile
    }
    fun setProfile(profile :String ) {
        this.profile =profile

    }

    fun getCover():String ?{
        return cover
    }
    fun setCover(cover:String ) {
        this.cover =cover

    }

    fun getStatus():String ?{
        return status
    }
    fun setStatus(status :String ) {
        this.status =status

    }

    fun getSearch():String ?{
        return search
    }
    fun setSearch(search :String ) {
        this.search =search

    }
    fun getFacebook():String ?{
        return facebook
    }
    fun setFacebook(facebook :String ) {
        this.facebook=facebook

    }    fun getInstagram():String ?{
        return instagram
    }
    fun setInstagram(instagram :String ) {
        this.instagram =instagram

    }
    fun getPortfolio():String ?{
        return portfolio
    }
    fun setPortfolio( portfolio :String ) {
        this.portfolio =portfolio
    }
}