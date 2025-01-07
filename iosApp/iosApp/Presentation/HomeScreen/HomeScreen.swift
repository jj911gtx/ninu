//
//  HomeScreen.swift
//  iosApp
//
//  Created by Jan Jeršin on 6. 1. 25.
//  Copyright © 2025 orgName. All rights reserved.
//
import SwiftUI

struct HomeScreen: View {
    @State private var changeScreen = false

    var body: some View {
        VStack {
            Button(action: {
                changeScreen.toggle()
            }) {
                Text("Change Screen")
            }
            .padding()

            if changeScreen {
                HomeScreenContent(navToEditFavouritesScreen: {})
            } else {
                HomeScreenConnect(pair: {})
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.black)
    }
}

struct HomeScreenContent: View {
    var navToEditFavouritesScreen: () -> Void

    var body: some View {
        ScrollView {
            VStack(spacing: 10) {
                BatteryView(percentage: 0.3)
                Image("device")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxWidth: .infinity)
                
                HStack(spacing: 10) {
                    ForEach(NINUSelection.allCases, id: \.self) { selection in
                        NINUSelectionItem(number: selection)
                    }
                }
                
                Button(action: navToEditFavouritesScreen) {
                    Text("Edit NINU Selections")
                        .foregroundColor(.white)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(5)
                }
                .frame(maxWidth: .infinity, alignment: .center)
                
                TipsPager()
            }
            .padding(10)
        }
    }
}

struct HomeScreenConnect: View {
    var pair: () -> Void = {}

    var body: some View {
        VStack {
            Image("logo-ninu")
                .padding(.top)

            Spacer()

            Image("device")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 400, height: 400)

//            DefaultButtonText(
//                action: {},
//                text: "Pair your device"
//            )
            .padding()

            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.black)
    }
}

struct BatteryView: View {
    var percentage: Float

    var body: some View {
        HStack(spacing: 5) {
            Circle()
                .fill(Color.black)
                .frame(width: 30, height: 30)
            Text("\(Int(percentage * 100))%")
                .foregroundColor(.white)
        }
        .padding(.horizontal, 15)
    }
}

struct NINUSelectionItem: View {
    var number: NINUSelection

    var body: some View {
        VStack(spacing: 5) {
            Circle()
                .strokeBorder(number.toColor(), lineWidth: 2)
                .frame(width: 50, height: 50)
                .overlay(Text("\(number.rawValue)").foregroundColor(.white))
            Text(number.description)
                .foregroundColor(.white)
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity)
    }
}

struct TipsPager: View {
    let tips = [
        ("NINU Tips & Tricks", "How To Properly Apply Fragrance"),
        ("NINU Tips & Tricks", "How To Properly Apply Fragrance"),
        ("NINU Tips & Tricks", "How To Properly Apply Fragrance")
    ]

    var body: some View {
        TabView {
            ForEach(tips, id: \.0) { tip in
                VStack {
                    Text(tip.0)
                        .font(.headline)
                        .foregroundColor(.white)
                    Text(tip.1)
                        .foregroundColor(.white)
                }
                .padding()
                .background(Color.gray)
                .cornerRadius(10)
            }
        }
        .tabViewStyle(PageTabViewStyle())
        .frame(height: 200)
    }
}

enum NINUSelection: String, CaseIterable {
    case N1, N2, N3, N

    var description: String {
        switch self {
        case .N:
            return "Last Uploaded"
        default:
            return "NINU Selection \(self.rawValue)"
        }
    }

    func toColor() -> Color {
        switch self {
        case .N1:
            return .red
        case .N2:
            return .green
        case .N3:
            return .blue
        case .N:
            return .yellow
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}


