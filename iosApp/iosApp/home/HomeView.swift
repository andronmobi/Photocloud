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

            HStack {
                Button(action: home.onTabHomeClick) {
                    Label("Home", systemImage: "house")
                        .labelStyle(VerticalLabelStyle())
                        .foregroundColor(.white)
                        .opacity(activeChild is HomeChild.PhotoListChild ? 1 : 0.7)
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
