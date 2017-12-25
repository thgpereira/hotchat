package br.com.thiago.hotchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.thiago.hotchat.entity.UserBlock;
import br.com.thiago.hotchat.exception.HotChatException;
import br.com.thiago.hotchat.repository.UserBlockRepository;
import br.com.thiago.hotchat.service.UserBlockService;
import br.com.thiago.hotchat.util.Messages;

@Service
public class UserBlockServiceImpl implements UserBlockService {

	@Autowired
	private UserBlockRepository userBlockRepository;

	@Override
	public UserBlock save(UserBlock userBlock) {
		return userBlockRepository.saveAndFlush(userBlock);
	}

	@Override
	public void controlUserBlock(UserBlock userBlock) throws HotChatException {
		UserBlock userBlocked = userBlockRepository.findByUserFromAndUserTo(userBlock.getUserFrom(),
				userBlock.getUserTo());
		if (userBlock.isBlock() && checkUserToBlock(userBlocked)) {
			userBlockRepository.saveAndFlush(userBlock);
		} else if (checkUserToUnlock(userBlocked)) {
			userBlockRepository.delete(userBlocked);
		} else {
			throw new HotChatException(Messages.userBlockBlocedError());
		}
	}

	private boolean checkUserToBlock(UserBlock userBlocked) throws HotChatException {
		if (userBlocked != null) {
			throw new HotChatException(Messages.userBlockAlreadyExists());
		}
		return true;
	}

	private boolean checkUserToUnlock(UserBlock userBlocked) throws HotChatException {
		if (userBlocked == null) {
			throw new HotChatException(Messages.userBlockNotExists());
		}
		return true;
	}

}
