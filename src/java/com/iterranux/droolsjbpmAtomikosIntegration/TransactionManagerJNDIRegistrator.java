/*
 * Copyright (c) 2013. Alexander Herwix
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * You can also obtain a commercial license. Contact: alex@herwix.com for further details.
 */

package com.iterranux.droolsjbpmAtomikosIntegration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;


/**
 * Workaround to make standalone TransactionManagers accessible via JNDI.
 */
public class TransactionManagerJNDIRegistrator {

    private static final Log log = LogFactory.getLog(TransactionManagerJNDIRegistrator.class);
    
    private String userTransactionLookup;
    
    private String transactionManagerLookup;
    
    private TransactionManager transactionManager;

    private UserTransaction userTransaction;
    
    @PostConstruct
    public void init(){
        log.debug("Registering TX to JNDI.");
        try {
            SimpleNamingContextBuilder.emptyActivatedContextBuilder();
            Context context = new InitialContext();

            context.bind(userTransactionLookup, userTransaction);
            context.bind(transactionManagerLookup, transactionManager);
                    
        } catch (NamingException e){
            log.error("JNDI Registration of the Transasction Manager failed.", e);
        }

    }

    public void setUserTransaction(UserTransaction userTransaction) {
        this.userTransaction = userTransaction;
    }

    public void setUserTransactionLookup(String userTransactionLookup) {
        this.userTransactionLookup = userTransactionLookup;
    }

    public void setTransactionManagerLookup(String transactionManagerLookup) {
        this.transactionManagerLookup = transactionManagerLookup;
    }

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
