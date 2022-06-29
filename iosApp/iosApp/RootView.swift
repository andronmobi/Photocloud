import SwiftUI
import sharedClient

struct RootView: View {

    private let root: Root

    @ObservedObject
    private var routerState: ObservableValue<RouterState<AnyObject, RootChild>>

    init(_ root: Root) {
        self.root = root
        routerState = (ObservableValue(root.routerState))
    }

    var body: some View {
        let activeChild = routerState.value.activeChild.instance

        switch activeChild {
        case let child as RootChild.LoginChild: LoginView(child.component)
        case let child as RootChild.HomeChild: HomeView(child.component)
        case activeChild as RootChild.SplashChild: SplashView()
        default: EmptyView()
        }
    }
}
