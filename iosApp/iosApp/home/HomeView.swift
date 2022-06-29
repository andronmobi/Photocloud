import SwiftUI
import sharedClient

struct HomeView: View {

    private let home: Home

    @ObservedObject
    private var routerState: ObservableValue<RouterState<AnyObject, HomeChild>>

    private var activeChild: HomeChild { routerState.value.activeChild.instance }

    init(_ home: Home) {
        self.home = home
        routerState = (ObservableValue(home.routerState))
    }

    var body: some View {
        VStack {
            ChildView(activeChild: activeChild)
                .frame(maxHeight: .infinity)

            HStack(spacing: 16) {
                Button(action: home.onTabHomeClick) {
                    Label("Photos", systemImage: "123.rectangle")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(activeChild is HomeChild.PhotoListChild ? 1 : 0.5)
                }

                Button(action: home.onTabSettingsClick) {
                    Label("Settings", systemImage: "list.bullet")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(activeChild is HomeChild.SettingsChild ? 1 : 0.5)
                }
            }
        }
    }
}

private struct ChildView: View {
    let activeChild: HomeChild

    var body: some View {
        switch activeChild {
        case activeChild as HomeChild.PhotoListChild: Text("PhotoList")
        case activeChild as HomeChild.SettingsChild: Text("Settings")
        default: EmptyView()
        }
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
