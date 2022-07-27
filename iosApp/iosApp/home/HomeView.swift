import SwiftUI
import sharedClient

struct HomeView: View {

    private let home: Home

    @ObservedObject
    private var bottomNavRouterState: ObservableValue<RouterState<AnyObject, HomeChild>>
    private var bottomActiveChild: HomeChild { bottomNavRouterState.value.activeChild.instance }

    init(_ home: Home) {
        self.home = home
        bottomNavRouterState = ObservableValue(home.bottomNavRouterState)
    }

    var body: some View {
        VStack {
            ChildView(activeChild: bottomActiveChild, home: home)
                .frame(maxHeight: .infinity)
            BottomBarView(activeChild: bottomActiveChild, home: home)
        }
    }
}

private struct ChildView: View {
    let activeChild: HomeChild
    let home: Home

    var body: some View {
        switch activeChild {
        case activeChild as HomeChild.HomeChild: HomeContentView(home)
        case let child as HomeChild.SettingsChild: SettingsView(child.component)
        default: EmptyView()
        }
    }
}

private struct HomeContentView: View {

    @ObservedObject
    private var homeRouterState: ObservableValue<RouterState<AnyObject, PhotoList>>
    private var component: PhotoList { homeRouterState.value.activeChild.instance }

    init(_ home: Home) {
        homeRouterState = ObservableValue(home.homeRouterState)
    }

    var body: some View {
        PhotoListView(component)
    }
}

private struct BottomBarView: View {
    let activeChild: HomeChild
    let home: Home

    var body: some View {
        HStack {
            Button(action: home.onTabHomeClick) {
                Label("Home", systemImage: "house")
                    .labelStyle(VerticalLabelStyle())
                    .foregroundColor(.white)
                    .opacity(activeChild is HomeChild.HomeChild ? 1 : 0.7)
            }
            .frame(maxWidth: .infinity)
            .padding(.top, 10)
            Button(action: home.onTabSettingsClick) {
                Label("Settings", systemImage: "gearshape")
                    .labelStyle(VerticalLabelStyle())
                    .foregroundColor(.white)
                    .opacity(activeChild is HomeChild.SettingsChild ? 1 : 0.7)
            }
            .frame(maxWidth: .infinity)
            .padding(.top, 10)
        }
        .frame(maxWidth: .infinity)
        .background(Color(red: 98 / 255, green: 0 / 255, blue: 242 / 255))
    }
}

private struct VerticalLabelStyle: LabelStyle {
    func makeBody(configuration: Configuration) -> some View {
        VStack(alignment: .center, spacing: 8) {
            configuration.icon
            configuration.title
        }
    }
}
