package by.mrc.schedule.teacher

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import by.mrc.schedule.R
import by.mrc.schedule.teacher.recycler.TeacherAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.swipeRefresh
import kotlinx.android.synthetic.main.fragment_teachers.*
import toothpick.Toothpick


class TeachersFragment: Fragment(), TeacherView {

    private val presenter = TeacherPresenter(this)
    private val adapter = TeacherAdapter()
    private var searchView: SearchView? = null

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Toothpick.inject(presenter, Toothpick.openScope("APP"))
        setHasOptionsMenu(true)
        return layoutInflater.inflate(R.layout.fragment_teachers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.wireUpdates()
        swipeRefresh.setOnRefreshListener {
            presenter.updateTeachers()
        }
        teacher_recycler.adapter = adapter
        presenter.updateTeachers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean = true

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.queryTextChanged(newText)
                return true
            }
        })
    }

    companion object {
        const val TAG = "TeachersFragment"
    }

    override fun renderTeachers(teachers: List<Teacher>, fromCache: Boolean, stopSpinner: Boolean) {
        if(stopSpinner) {
            swipeRefresh.isRefreshing = false
        }
        if(teachers.isEmpty()) {
            emptyView.visibility = View.VISIBLE
            teacher_recycler.visibility = View.GONE
        } else {
            emptyView.visibility = View.GONE
            teacher_recycler.visibility = View.VISIBLE
        }
        adapter.populate(teachers)
        snackbar?.dismiss()
        if(fromCache) {
            snackbar = Snackbar
                .make(teacherCoordinator, "Не удалось обновить список преподавателей", Snackbar.LENGTH_LONG)
            snackbar?.view?.setBackgroundColor(Color.RED)
            snackbar?.view?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.textSize = 18f
            snackbar?.show()
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }
}