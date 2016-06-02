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

package com.netflix.spinnaker.gate.controllers

import com.netflix.spinnaker.gate.services.EventService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CompileStatic
@RestController
class EventController {

  @Autowired
  EventService eventService

  @RequestMapping(value = "/webhooks/{type}/{source}", method = RequestMethod.POST)
  void webhooks(@PathVariable("type") String type, @PathVariable("source") String source, @RequestBody Map event) {
    eventService.webhooks(type, source, event)
  }

  @RequestMapping(value = "/webhooks/{type}", method = RequestMethod.POST)
  void webhooks(@PathVariable("type") String type, @RequestBody Map event) {
    eventService.webhooks(type, event)
  }
}
