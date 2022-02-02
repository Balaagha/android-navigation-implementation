package com.example.androidnavigationimplementation

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.example.androidnavigationimplementation.databinding.ActivityMainBinding
import com.example.androidnavigationimplementation.databinding.NavHeaderMainBinding
import com.example.androidnavigationimplementation.ui.viewmodel.LettersViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    /**
     * We’ll use navController to navigate from one fragment to another.
     * Import findNavController from androidx.navigation.findNavController.
     */
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    /**
     * default value = { this }
     * for activity scope = { requireActivity() }
     */
    private val viewModelFactoryOwner: (() -> ViewModelStoreOwner) = {
        navController.getViewModelStoreOwner(R.id.nav_graph)
    }

    /**
     * In the fragment we can use [createViewModelLazy] method for it. (with this library: androidx.fragment:fragment-ktx:1.3.0-alpha06)
     */
    private val factoryProducer: ViewModelProvider.Factory by lazy { defaultViewModelProviderFactory }

    private val lettersViewModel: LettersViewModel by ViewModelLazy(
        LettersViewModel::class,
        { viewModelFactoryOwner.invoke().viewModelStore },
        { factoryProducer }
    )

    /**
     * [appBarConfiguration] defines which fragments are the top level fragments so the drawerLayout and hamburger icon can work properly.
     */
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.sentFragment,
                R.id.inboxFragment
            ), drawerLayout
        )
    }

    private lateinit var headerBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setSupportActionBar(toolbar)
        setupNavigation()
        setupViewModel()
        setupViewsListener()
    }

    override fun onDestroy() {
        lettersViewModel?.apply { closeDb() }
        super.onDestroy()
    }

    private fun setupDataBinding() {
        val activityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        headerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.nav_header_main, activityMainBinding.navView, false
        )
        headerBinding.ivEdit.setOnClickListener {
            navController.navigate(R.id.editProfileFragment)
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        activityMainBinding.navView.addHeaderView(headerBinding.root)
    }

    private fun setupNavigation() {
        /**
         * Connect [navController] with [drawerLayout]
         */
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        /**
         * Connect [toolbar], [navController] and [appBarConfiguration] together.
         * This makes your [navController] aware of top-level fragments defined in appBarConfiguration.
         */
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        /**
         *  [fab] action button and [toolbar] based on the destination fragment.
         *  Here you want to hide [fab] action button when you’re on [CreateLetterFragment], [PresentationFragment] and [EditProfileFragment].
         *  On [PresentationFragment], hide the [toolbar] so that we can read your love letters without distraction.

         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.createLetterFragment,
                    R.id.presentationFragment,
                    R.id.editProfileFragment)
            ) {
                fab.hide()
            } else {
                fab.show()
            }
            toolbar.isVisible = destination.id != R.id.presentationFragment
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupViewModel() {
        try {
            /** [This's OTHER WAY FOR INITIALIZATION OF VIEWMODEL]
             * Instead of the usual way of getting ViewModelProviders from Activity or Fragment like ViewModelProviders.of(this),
             * here you get ViewModelProvider from navigation graph id, which is R.id.nav_graph.
             * This ensures you’re using the same ViewModel across entire graph.
             */
//            val viewModelProvider = ViewModelProvider(
//                navController.getViewModelStoreOwner(R.id.nav_graph),
//                ViewModelProvider.AndroidViewModelFactory(application)
//            )
            /**
             * Get your LettersViewModel from viewModelProvider, which you created in the line above.
             */
//            lettersViewModel = viewModelProvider[LettersViewModel::class.java]
            /**
             * This is the data binding logic for the header in the navigation drawer.
             * If you’re curious, check nav_header_main.xml. The header binding requires the viewModel instance of LettersViewModel class.
             */
            headerBinding.viewModel = lettersViewModel
            // Load profile information from SharedPreferences.
            lettersViewModel?.loadProfile()  //4
        } catch (e: IllegalArgumentException) {
            /**
             * An exception happens when the app is launched via a deep link.
             * Navigation component can’t handle the initialization of a ViewModel scoped to Navigation graph.
             * The temporary solution is to catch the exception.
             */
            e.printStackTrace()
        }
    }

    private fun setupViewsListener() {
        navView.setNavigationItemSelectedListener(this)
        fab.setOnClickListener {
            navController.navigate(R.id.createLetterFragment)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Since [InboxFragment] is the home fragment, you use popBackStack to remove other fragments from the navigation stack.
     * The second parameter indicates whether [InboxFragment] should also pop out of the stack
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_inbox -> {
                navController.popBackStack(R.id.inboxFragment, false)
            }
            R.id.nav_sent -> {
                navController.navigate(R.id.sentFragment)
            }
            R.id.nav_privacy_policy -> {
                navController.navigate(Uri.parse("loveletter://agreement/privacy-policy"))
            }
            R.id.nav_terms_of_service -> {
                navController.navigate(Uri.parse("loveletter://agreement/terms-of-service"))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}