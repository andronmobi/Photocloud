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
            List(model.value.dirs, id: \.self) { photoDir in
                Button("Dir: " + photoDir.name) {
                    self.photoList.onDirClicked(dirId: photoDir.id)
                }
            }
            List(model.value.photoImages, id: \.self) { photoImage in
                let uiImage = (photoImage.image as KotlinByteArray).toUiImage() ?? UIImage()
                Image(uiImage: uiImage)
            }
        }
    }

}
