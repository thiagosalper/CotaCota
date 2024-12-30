package br.com.salperdev.cotacota.ui.navigation

import android.app.Activity
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.salperdev.cotacota.ui.navigation.FornecedoresDestinationsArgs.FORNECEDOR_ID_ARG
import br.com.salperdev.cotacota.ui.navigation.FornecedoresDestinationsArgs.TITLE_ARG
import kotlinx.coroutines.CoroutineScope
import androidx.navigation.NavType
import androidx.compose.runtime.remember

@Composable
fun FornecedoresNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    startDestination: String = FornecedoresDestinations.FORNECEDORES_ROUTE,
    navAction: FornecedoresNavigationActions = remember(navController) {
        FornecedoresNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            FornecedoresDestinations.FORNECEDORES_ROUTE,
            arguments = listOf(
                navArgument(USER_MESSAGE_ARG) { type = NavType.IntType; defaultValue = 0 }
            )
        ) {
            FornecedorListScreen(
                addFornecedor = { navActions.navigateToAddEditFornecedor(R.string.add_fornecedor, null) },
                editFornecedor = { fornecedorId -> navActions.navigateToAddEditFornecedor(R.string.edit_fornecedor, fornecedorId) }
            )
        }
        composable(
            FornecedoresDestinations.ADD_EDIT_FORNECEDOR_ROUTE,
            arguments = listOf(
                navArgument(TITLE_ARG) { type = NavType.IntType },
                navArgument(FORNECEDOR_ID_ARG) { type = NavType.StringType; nullable = true },
            )
        ) { entry ->
            val fornecedorId = entry.arguments?.getString(FORNECEDOR_ID_ARG)
            AddEditFornecedorScreen(
                onFornecedorUpdate = {
                    navAction.navigateToFornecedores(
                        if (fornecedorId == null) ADD_EDIT_RESULT_OK else EDIT_RESULT_OK
                    )
                },
            )
        }
    }
}

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3