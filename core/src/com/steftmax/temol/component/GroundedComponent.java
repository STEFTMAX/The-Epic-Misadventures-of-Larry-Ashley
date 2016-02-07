package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * @author pieter3457
 *
 */
public class GroundedComponent implements Component {

	public boolean isGrounded = false;

	public Contact groundContact;
	
	public Fixture feetFixture, groundFixture;

	public int feetFixtureIndex;
	
	public float relativeFeetHeight;

	public GroundedComponent(int feetFixtureIndex, float relativeFeetHeight) {
		this.feetFixtureIndex = feetFixtureIndex;
		this.relativeFeetHeight = relativeFeetHeight;
	}

}
