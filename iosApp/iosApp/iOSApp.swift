import SwiftUI
import sharedClient

@main
struct iOSApp: App {
    
    let loader: PhotocloudLoader

    init() {
        loader = PhotocloudLoader()
        loader.getConfig { config, error in
            print(config)
        }
        print("photocloud")
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
