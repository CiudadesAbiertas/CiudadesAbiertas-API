IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[TranslateCA]') AND type in (N'FN', N'IF',N'TF', N'FS', N'FT'))
	DROP FUNCTION [TranslateCA]