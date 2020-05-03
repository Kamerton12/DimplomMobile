package by.mrc.schedule.teacher

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import by.mrc.schedule.R
import by.mrc.schedule.teacher.recycler.TeacherAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.swipeRefresh
import kotlinx.android.synthetic.main.fragment_teachers.*


class TeachersFragment: Fragment(), TeacherView {

    private val presenter = TeacherPresenter(this)
    private val adapter = TeacherAdapter()
    private var searchView: SearchView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return layoutInflater.inflate(R.layout.fragment_teachers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
        }
        teacher_recycler.adapter = adapter
        adapter.populate(listOf(Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("lol", "hey", "lala", "+375 (44) 77-33-782", "email: s312@tut.by"),Teacher("Леонид", "Назаров", "Викторович", "+375 (44) 77-33-782", "Преподаватель высшей категории дисциплин общепрофессионального и специального циклов\n" +
                "\n" +
                "e-mail: leon_naz1@tut.by\n" +
                "\n" +
                "тел. (017) 351-03-51")))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = true

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })
    }

    companion object {
        const val TAG = "TeachersFragment"
    }
}