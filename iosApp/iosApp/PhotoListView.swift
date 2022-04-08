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
        List(model.value.photos, id: \.self) { photo in
            Text(photo.id)
        }
    }

}

