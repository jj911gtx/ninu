//
//  File.swift
//  iosApp
//
//  Created by Jan Jeršin on 3. 12. 24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared



func collect<T>( flow: CommonStateFlow<T>, onEach : @escaping (T) -> Void) async {
    var countinuation : CheckedContinuation<Void, Never>?
    let cancellable = flow.startCollection(onEach: {value in onEach (value!)}) {
        countinuation?.resume ()
    }
    await withTaskCancellationHandler(operation: {
        await withCheckedContinuation{value in countinuation = value}
    }, onCancel: {
        cancellable.onCancel()
    })
}
