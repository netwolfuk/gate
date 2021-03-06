/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.netflix.spinnaker.gate.services

import com.netflix.spinnaker.gate.services.commands.HystrixFactory
import com.netflix.spinnaker.gate.services.internal.ClouddriverService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@CompileStatic
@Component
class ImageService {
  private static final String GROUP = "images"

  @Autowired
  ClouddriverService clouddriverService

  @Autowired
  ProviderLookupService providerLookupService

  List<Map> getForAccountAndRegion(String provider, String account, String region, String imageId) {
    HystrixFactory.newListCommand(GROUP, "getImagesForAccountAndRegion-${providerLookupService.providerForAccount(account)}") {
      clouddriverService.getImageDetails(provider, account, region, imageId)
    } execute()
  }

  List<Map> search(String provider, String query, String region, String account) {
    HystrixFactory.newListCommand(GROUP, "searchImages-${providerLookupService.providerForAccount(account)}") {
      clouddriverService.findImages(provider, query, region, account)
    } execute()
  }
}
