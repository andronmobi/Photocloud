import SwiftUI
import sharedClient

struct HomeView: View {

    private let home: Home

    @ObservedObject
    private var routerState: ObservableValue<RouterState<AnyObject, HomeChild>>

    init(_ home: Home) {
        self.home = home
        routerState = (ObservableValue(home.routerState))
    }

    var body: some View {
        let activeChild = routerState.value.activeChild.instance

        switch activeChild {
        case activeChild as HomeChild.PhotoListChild: Text("PhotoList")
        case activeChild as HomeChild.SettingsChild: Text("Settings")
        default: EmptyView()
        }
    }
}
