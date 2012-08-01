/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.batoo.jpa.core.impl.criteria.join;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.SingularAttribute;

import org.batoo.jpa.core.impl.model.attribute.SingularAttributeImpl;
import org.batoo.jpa.core.impl.model.mapping.EmbeddedMapping;
import org.batoo.jpa.core.impl.model.mapping.JoinedMapping;
import org.batoo.jpa.core.impl.model.mapping.JoinedMapping.MappingType;
import org.batoo.jpa.core.impl.model.mapping.Mapping;
import org.batoo.jpa.core.impl.model.mapping.SingularAssociationMapping;

/**
 * Joins for singular attributes.
 * 
 * @param <Z>
 *            the source type
 * @param <X>
 *            the target type
 * 
 * @author hceylan
 * @since $version
 */
public class SingularJoin<Z, X> extends AbstractJoin<Z, X> {

	/**
	 * @param parent
	 *            the parent
	 * @param mapping
	 *            the mapping
	 * @param jointType
	 *            the join type
	 * 
	 * @since $version
	 * @author hceylan
	 */
	public SingularJoin(AbstractFrom<?, Z> parent, JoinedMapping<? super Z, ?, X> mapping, JoinType jointType) {
		super(parent, mapping, jointType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected <C, Y> Mapping<? super X, C, Y> getMapping(String name) {
		Mapping<? super X, ?, ?> child = null;

		if (this.getMapping().getMappingType() == MappingType.EMBEDDABLE) {
			child = ((EmbeddedMapping<? super Z, X>) this.getMapping()).getChild(name);
		}
		else {
			child = ((SingularAssociationMapping<? super Z, X>) this.getMapping()).getType().getRootMapping().getChild(name);
		}

		if (child == null) {
			throw this.cannotDereference(name);
		}

		return (Mapping<? super X, C, Y>) child;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public SingularAttribute<? super Z, X> getModel() {
		return (SingularAttributeImpl<? super Z, X>) super.getAttribute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Join<Z, X> on(Expression<Boolean> restriction) {
		throw this.notSupported();
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public Join<Z, X> on(Predicate... restrictions) {
		throw this.notSupported();
	}
}
