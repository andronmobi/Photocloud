import SwiftUI

struct SplashView: View {
    var body: some View {
        Image("ic_cloud").resizable().frame(width: 160, height: 160).scaledToFit()
            .overlay(
                Text("Photocloud")
                    .bold()
                    .foregroundColor(Color.white)
                    .padding(.horizontal, 8)
                    .padding(.top, 16)
            )
    }
}

struct SplashView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
