import androidx.compose.ui.window.ComposeUIViewController
import com.devom.pandit.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
