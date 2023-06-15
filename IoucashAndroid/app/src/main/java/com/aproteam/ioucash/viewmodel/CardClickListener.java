package com.aproteam.ioucash.viewmodel;

import com.aproteam.ioucash.model.Transaction;

/**
 * Interface for handling click events on transaction cards.
 */
public interface CardClickListener {

	void onCardClicked(Transaction transaction);

}