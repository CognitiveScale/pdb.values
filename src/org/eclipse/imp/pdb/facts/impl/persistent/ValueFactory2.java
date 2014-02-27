/*******************************************************************************
 * Copyright (c) 2013 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI  
 *******************************************************************************/
package org.eclipse.imp.pdb.facts.impl.persistent;

import org.eclipse.imp.pdb.facts.IMap;
import org.eclipse.imp.pdb.facts.IMapWriter;
import org.eclipse.imp.pdb.facts.ISet;
import org.eclipse.imp.pdb.facts.ISetWriter;
import org.eclipse.imp.pdb.facts.IValue;
import org.eclipse.imp.pdb.facts.type.Type;
import org.eclipse.imp.pdb.facts.type.TypeFactory;

public class ValueFactory2 extends org.eclipse.imp.pdb.facts.impl.fast.ValueFactory {

	protected ValueFactory2() {
		super();
	}

	private static class InstanceKeeper {
		public final static ValueFactory2 instance = new ValueFactory2();
	}

	public static ValueFactory2 getInstance() {
		return InstanceKeeper.instance;
	}

//	public ISetWriter setWriter(Type elementType) {
//		return new TemporarySetWriter2(elementType);
//	}
//
//	public ISetWriter setWriter() {
//		return new TemporarySetWriter2();
//	}
//
//	public ISetWriter relationWriter(Type tupleType) {
//		return new TemporarySetWriter2(tupleType);
//	}
//
//	public ISetWriter relationWriter() {
//		return new TemporarySetWriter2();
//	}
//
//	public ISet set(Type elementType) {
//		return setWriter().done();
//	}
//
//	public ISet set(IValue... elements) {
//		ISetWriter setWriter = setWriter();
//		setWriter.insert(elements);
//		return setWriter.done();
//	}
//
//	public ISet relation(Type tupleType) {
//		return relationWriter(tupleType).done();
//	}
//
//	public ISet relation(IValue... elements) {
//		return set(elements);
//	}

	@Override
	public IMapWriter mapWriter(Type keyType, Type valueType) {
		return mapWriter(TypeFactory.getInstance().mapType(keyType, valueType));
	}

	@Override
	public IMapWriter mapWriter(Type mapType) {
		return new TemporaryMapWriter2(mapType);
	}

	@Override
	public IMapWriter mapWriter() {
		return new TemporaryMapWriter2();
	}

	@Override
	public IMap map(Type mapType) {
		return mapWriter(mapType).done();
	}

	@Override
	public IMap map(Type keyType, Type valueType) {
		return mapWriter(keyType, valueType).done();
	}	
	
	@Override
	public String toString() {
		return "VF_PDB_PERSISTENT_2";
	}

}
