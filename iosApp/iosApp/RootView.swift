import SwiftUI
import sharedClient

struct RootView: View {

    private let root: Root

    @ObservedObject
    private var routerState: ObservableValue<RouterState<AnyObject, Screen>>

    init(_ root: Root) {
        self.root = root
        routerState = (ObservableValue(root.routerState))
    }

    var body: some View {
        let activeChild = routerState.value.activeChild.instance

        switch activeChild {
        case let child as Screen.LoginScreen: LoginView(login: child.component)
        case activeChild as Screen.HomeScreen: HomeView()
        case activeChild as Screen.SplashScreen: SplashView()
        default: EmptyView()
        }
    }
}
