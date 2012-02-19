/*
 * Copyright 2011-2012 Gregory P. Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.mythtv.util.unsupported;

/**
 * This interface represents the contract to handle unsupported functionality.
 * Common implementations are to throw an {@link UnsupportedOperationException}
 * or log the event.
 * 
 * @author Gregory P. Moyer
 */
public interface UnsupportedHandler 
{
	/**
	 * Handle the unsupported operation event.
	 * 
	 * @param opDescription
	 *            a description of the operation that was attempted, but not
	 *            supported
	 */
	public void handle(String opDescription);
}
