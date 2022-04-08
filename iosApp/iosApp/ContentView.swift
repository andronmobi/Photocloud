import SwiftUI
import sharedClient

struct ContentView: View {
    
    @State
    private var componentHolder = ComponentHolder(factory: RootComponent.init)

    var body: some View {
        RootView(componentHolder.component)
            .onAppear { LifecycleRegistryExtKt.resume(self.componentHolder.lifecycle) }
            .onDisappear { LifecycleRegistryExtKt.stop(self.componentHolder.lifecycle) }
    }
}

class ComponentHolder<T> {
   let lifecycle: LifecycleRegistry
   let component: T

   init(factory: (ComponentContext) -> T) {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let component = factory(DefaultComponentContext(lifecycle: lifecycle))
        self.lifecycle = lifecycle
        self.component = component

        lifecycle.onCreate()
   }

    deinit {
        lifecycle.onDestroy()
    }
}
