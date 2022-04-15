import SwiftUI
import sharedClient

struct PhotoListView: View {
    
    private let photoList: PhotoList

    @ObservedObject
    private var model: ObservableValue<PhotoListModel>

    init(_ photoList: PhotoList) {
        self.photoList = photoList
        self.model = ObservableValue(photoList.models)
    }

    var body: some View {
        if (photoList.isInitial) {
            photosView()
        } else {
            NavigationView {
                VStack {
                    photosView()
                }
                .navigationBarTitle(Text("Current dir name"), displayMode: .inline)
                .navigationBarItems(
                    leading: Image(systemName: "arrow.backward")
                        .aspectRatio(contentMode: .fit)
                        .imageScale(.large)
                        .foregroundColor(.blue)
                        .onTapGesture {
                            photoList.onBackClicked()
                        }
                )
            }
        }
    }

    func photosView() -> some View {
        return Form {
            List(model.value.dirs, id: \.self) { dir in
                Button("Dir: " + dir.id) {
                    self.photoList.onDirClicked(dir: dir)
                }
            }
            List(model.value.images, id: \.self) { image in
                let uiImage = (image as KotlinByteArray).toUiImage() ?? UIImage()
                Image(uiImage: uiImage)
            }
        }
    }

}
