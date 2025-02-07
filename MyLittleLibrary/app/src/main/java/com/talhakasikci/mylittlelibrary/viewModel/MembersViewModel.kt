package com.talhakasikci.mylittlelibrary.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.talhakasikci.mylittlelibrary.model.Members
import com.talhakasikci.mylittlelibrary.roomdb.BooksDB
import com.talhakasikci.mylittlelibrary.roomdb.MembersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MembersViewModel(application: Application) : AndroidViewModel(application) {

    private val membersDao: MembersDao = BooksDB.getDatabase(application).MembersDao()

    val allMembers: LiveData<List<Members>> = membersDao.getAll()

    fun insert(member: Members) {
        viewModelScope.launch(Dispatchers.IO) {
            membersDao.MemberInsert(member)
        }
    }

    fun delete(member: Members) {
        viewModelScope.launch(Dispatchers.IO) {
            membersDao.MemberDelete(member)
        }
    }
}
