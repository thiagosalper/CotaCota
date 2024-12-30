package br.com.salperdev.cotacota.ui.navigation

import androidx.navigation.NavHostController
import br.com.salperdev.cotacota.ui.navigation.FornecedoresDestinationsArgs.FORNECEDOR_ID_ARG
import br.com.salperdev.cotacota.ui.navigation.FornecedoresDestinationsArgs.USER_MESSAGE_ARG
import br.com.salperdev.cotacota.ui.navigation.FornecedoresScreens.ADD_EDIT_FORNECEDOR_SCREEN
import br.com.salperdev.cotacota.ui.navigation.FornecedoresScreens.FORNECEDORES_SCREEN

private object FornecedoresScreens {
    const val FORNECEDORES_SCREEN = "fornecedores"
    const val ADD_EDIT_FORNECEDOR_SCREEN = "addEditFornecedor"
}

object FornecedoresDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val FORNECEDOR_ID_ARG = "fornecedorId"
    const val TITLE_ARG = "title"
}

object FornecedoresDestinations {
    const val FORNECEDORES_ROUTE = "$FORNECEDORES_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val ADD_EDIT_FORNECEDOR_ROUTE = "$ADD_EDIT_FORNECEDOR_SCREEN/{$TITLE_ARG}?$FORNECEDOR_ID_ARG={$FORNECEDOR_ID_ARG}"
}

class FornecedoresNavigationActions(private val navController: NavHostController) {
    fun navigateToFornecedores(userMessage: Int = 0) {
        val navigatesFromDrawer = userMessage == 0
        navController.navigate(
            FORNECEDORES_SCREEN.let {
                if (userMessage != 0) ""
            }
        )
    }
}