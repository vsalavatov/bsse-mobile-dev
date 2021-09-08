package com.vadimsalavatov.mobiledev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val usersRecyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
        usersRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val userAdapter = UserAdapter()
        usersRecyclerView.adapter = userAdapter
        userAdapter.userList = loadUsers()
        userAdapter.notifyDataSetChanged()
    }

    fun loadUsers(): List<User> {
        return listOf(
            User("avatar url", "биба", "б09"),
            User("avatar url2", "боба", "б10"),
            User("avatar url", "биба", "б09"),
            User("avatar url2", "боба", "б10"),
            User("avatar url", "биба", "б09"),
            User("avatar url2", "боба", "б10"),
            User("avatar url", "биба", "б09"),
            User("avatar url2", "боба", "б10"),
            User("avatar url", "биба", "б09"),
            User("avatar url2", "боба", "б10"),
            User("avatar url", "биба", "б09"),
            User("avatar url2", "боба", "б10"),
        )
    }
}