import SwiftUI
import sharedClient

extension String {
    func load() -> UIImage {
        do {
            guard let url = URL(string: self) else {
                return UIImage()
            }
            let data: Data = try Data(contentsOf: url)
            return UIImage(data: data) ?? UIImage()
        } catch {}
        return UIImage()
    }
}

struct ContentView: View {
	let greet = Greeting().greeting()

	var body: some View {
        Image(uiImage: "https://www.pngall.com/wp-content/uploads/1/Android.png".load())
            .resizable()
            .frame(width: 100.0, height: 100.0)
		Text(greet)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
