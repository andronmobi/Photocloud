import SwiftUI
import sharedClient

struct RootView: View {
    
    private let root: Root
    
    @ObservedObject
    private var routerState: ObservableValue<RouterState<AnyObject, RootChild>>

    init(_ root: Root) {
        self.root = root
        self.routerState = (ObservableValue(root.routerState))
    }

    var body: some View {
        let activeChild = self.routerState.value.activeChild.instance
        if let listChild = activeChild as? RootChild.ListChild {
            PhotoListView(listChild.photoList)
        } else {
            Text("loading...")
        }
    }
}
