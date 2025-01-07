//
//  BaseButton.swift
//  iosApp
//
//  Created by Jan Jeršin on 6. 1. 25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation


import SwiftUI


struct DefaultButton<Content: View>: View {
    var action: () -> Void
    var enabled: Bool = true
    var displayDisabled: Bool = false
    var content: () -> Content

    var body: some View {
        Button(action: action) {
            ZStack {
                // Background with gradient
                RoundedRectangle(cornerRadius: 10)
                    .fill(
                        LinearGradient(
                            gradient: Gradient(colors: backgroundColors),
                            startPoint: .leading,
                            endPoint: .trailing
                        )
                    )
                    .frame(maxWidth: .infinity)
                    .overlay(
                        // Border if disabled
                        RoundedRectangle(cornerRadius: 10)
                            .strokeBorder(
                                LinearGradient(
                                    gradient: Gradient(colors: borderColors),
                                    startPoint: .top,
                                    endPoint: .bottom
                                ),
                                lineWidth: displayDisabled || !enabled ? 1 : 0
                            )
                    )
                
                // Content
                content()
                    .padding(.vertical, 15)
                    .padding(.horizontal, 30)
            }
        }
        .disabled(!enabled)
        .frame(maxWidth: UIScreen.main.bounds.width * 0.8)
        .buttonStyle(PlainButtonStyle())
    }

    // Computed properties for colors
    private var backgroundColors: [Color] {
        if !displayDisabled && enabled {
            return [.secondaryLight, .secondaryDark]
        } else {
            return [
                .white.opacity(0.10),
                .secondaryLight2.opacity(0.3)
            ]
        }
    }

    private var borderColors: [Color] {
        if displayDisabled || !enabled {
            return [
                .secondaryLight3.opacity(0.10),
                .secondaryLight1.opacity(0.26)
            ]
        } else {
            return [Color.clear, Color.clear]
        }
    }
}



struct BaseButton_Previews: PreviewProvider {
    static var previews: some View {
        DefaultButton(action: {
            // Example action
            print("Button pressed")
        }) {
            Text("hohohoh")
        }
    }
}
