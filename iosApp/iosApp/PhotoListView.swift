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
        Form {
            ScrollView(.horizontal) {
                HStack(spacing: 16) {
                    ForEach(model.value.dirs, id: \.self.id) { photoDir in
                        Button(action: {
                            photoList.onDirClicked(dirId: photoDir.id)
                        }, label: {
                            Image("ic_dir").resizable().frame(width: 120, height: 120).scaledToFit()
                                    .overlay(Text(photoDir.name))
                                    .padding(.bottom, 8)
                        })
                    }
                }
                Spacer().frame(height: 4)
            }
            List(model.value.photoImages, id: \.self) { photoImage in
                let uiImage = (photoImage.image as KotlinByteArray).toUiImage() ?? UIImage()
                Image(uiImage: uiImage)
            }
        }
    }

}
