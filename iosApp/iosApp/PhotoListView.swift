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
        return List(model.value.files, id: \.self) { file in
            if let dir = file as? Dir {
                Button("Dir: " + dir.id) {
                    self.photoList.onDirClicked(dir: dir)
                }
            } else {
                Text("Photo: " + file.id)
            }
        }
    }

}

