import SwiftUI
import sharedClient

@main
struct iOSApp: App {

    init() {
        let loader = PhotocloudLoader()
        loader.getConfig { config, error in
            let conf: CommonAllConfig = config!
            print(conf.rootDir)
            loader.getFiles(dir: conf.rootDir) { files, error in
                for f in files! {
                    print(f)
                }
            }
        }
        print("photocloud")
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
