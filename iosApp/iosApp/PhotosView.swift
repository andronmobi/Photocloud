import SwiftUI

struct PhotosView: View {
    
    @ObservedObject var viewModel = PhotosViewModel()
    
    var body: some View {
        List(viewModel.photos, id: \.self) { photo in
            Text(photo.id)
            Image(uiImage: photo.image)
        }
        .onAppear {
            self.viewModel.loadPhotos()
        }
    }
}

struct PhotosView_Previews: PreviewProvider {
    static var previews: some View {
        PhotosView()
    }
}
