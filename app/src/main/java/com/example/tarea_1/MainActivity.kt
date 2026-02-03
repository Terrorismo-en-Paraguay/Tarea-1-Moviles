package com.example.tarea_1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.NavigationUI
import com.example.tarea_1.databinding.ActivityMainBinding
import com.example.tarea_1.fragment.ListFragment
import com.example.tarea_1.fragment.TabFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isTopMenuVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->

            val showAppBar = destination.id == R.id.tabFragment ||
                    destination.id == R.id.contactFragment ||
                    destination.id == R.id.preferencesFragment || destination.id == R.id.FavFragment
            val showMenuItems = destination.id == R.id.tabFragment || destination.id == R.id.FavFragment


            binding.fab.isVisible = showAppBar
            binding.bottomNavigation.isVisible = destination.id != R.id.FragmentoUno && destination.id != R.id.FragmentoDos

            binding.topAppBar.isVisible = showAppBar

            val menu = binding.topAppBar.menu
            menu.findItem(R.id.action_search)?.isVisible = showMenuItems
            menu.findItem(R.id.action_sort)?.isVisible = showMenuItems
            if (destination.id == R.id.tabFragment) {
                binding.fab.isVisible = true
            }else{
                binding.fab.isVisible = false
            }
            if (showAppBar) {
                binding.topAppBar.setNavigationIcon(R.drawable.ic_menu) 

                binding.topAppBar.setNavigationOnClickListener {
                     binding.drawerLayout.open()
                }
            } else {

                binding.topAppBar.setNavigationIcon(null)
                binding.topAppBar.setNavigationOnClickListener { navController.navigateUp() }
            }
            
            isTopMenuVisible = showMenuItems
        }
        binding.bottomNavigation.setupWithNavController(navController)
        binding.navigationView.setupWithNavController(navController)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.tabFragment,
                R.id.FavFragment,
                R.id.contactFragment,
                R.id.preferencesFragment
            ),
            binding.drawerLayout
        )

        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)


        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    navController.navigate(R.id.FragmentoUno)
                    binding.drawerLayout.close()
                    true
                }
                R.id.FavFragment1 ->{
                    navController.navigate(R.id.FavFragment)
                    binding.drawerLayout.close()
                    true
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(menuItem, navController)
                    binding.drawerLayout.close()
                    true
                }
            }
        }


        val searchItem = binding.topAppBar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as? android.widget.SearchView
        searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                val listViewModel = ViewModelProvider(this@MainActivity).get(com.example.tarea_1.viewmodels.ListViewModel::class.java)
                listViewModel.updateSearchQuery(newText ?: "")
                return true
            }
        })
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            val listViewModel = ViewModelProvider(this).get(com.example.tarea_1.viewmodels.ListViewModel::class.java)

            when (menuItem.itemId) {
                R.id.action_sort -> {
                    listViewModel.toggleSortOrder()
                    true
                }
                R.id.nav_logout_top ->{
                    navController.navigate(R.id.FragmentoUno)
                    true
                }
                else -> false
            }
        }

    }
}