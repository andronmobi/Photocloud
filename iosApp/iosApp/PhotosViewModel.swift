import Foundation
import SwiftUI
import sharedClient

struct Photo: Hashable {
    let id: String
    let image: UIImage
}

class PhotosViewModel : ObservableObject {
    @Published private(set) var photos: [Photo] = []
    
    private let loader = PhotocloudLoader()

    func loadPhotos() {
        loader.getConfig { config, error in
            let conf: CommonAllConfig = config!
            self.loader.getFiles(dir: conf.rootDir) { files, error in
                for f in files! {
                    if let dir = f as? CommonAllDir {
                        print("dir: \(dir)")
                    } else {
                        print("photo: \(f)")
                        self.loader.getImageData(photoId: f.id) { data, error in
                            let byteArray: KotlinByteArray = data!
                            let img = byteArray.toUiImage() ?? UIImage()
                            self.photos.append(contentsOf: [Photo(id: f.id, image: img)])
                        }
                    }
                }
            }
        }
    }
}
