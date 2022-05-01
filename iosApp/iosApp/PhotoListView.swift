import SwiftUI
import sharedClient

extension View {
    @ViewBuilder func phoneOnlyStackNavigationView() -> some View {
        if UIDevice.current.userInterfaceIdiom == .phone {
            self.navigationViewStyle(.stack)
        } else {
            self
        }
    }
}

struct PhotoListView: View {

    private let photoList: PhotoList

    @ObservedObject
    private var model: ObservableValue<PhotoListModel>

    init(_ photoList: PhotoList) {
        self.photoList = photoList
        model = ObservableValue(photoList.models)
    }

    var body: some View {
        if (photoList.isInitial) {
            foldersAndPhotos()
        } else {
            NavigationView {
                VStack(alignment: .leading) {
                    foldersAndPhotos()
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
            }.phoneOnlyStackNavigationView()
        }
    }

    func foldersAndPhotos() -> some View {
        VStack(alignment: .leading, spacing: 10) {
            folders()
            photosView()
        }
            .frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment: .topLeading
            ).padding([.horizontal], 8)
    }

    func folders() -> some View {
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
        }
    }

    func photosView() -> some View {
        ScrollView(.vertical) {
            let columns: [GridItem] = [GridItem(.adaptive(minimum: 120))]
            LazyVGrid(columns: columns) {
                ForEach(model.value.photoImages, id: \.self.id) { photoImage in
                    // try to do converting outside of scroll view to increase a performance
                    let uiImage = (photoImage.image as KotlinByteArray).toUiImage() ?? UIImage()
                    Image(uiImage: uiImage)
                        .resizable()
                        .scaledToFill()
                        .frame(width: 120, height: 120, alignment: .center)
                        .clipped()
                        .cornerRadius(4)
                }
            }
        }
    }

}
